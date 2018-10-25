package main

import (
	"net/http"
	"time"
)

var ch = make(chan int)

func main() {

	t1 := time.Now().UnixNano()
	var i int = 0
	for i < 500 {
		go httpGet()
		i++
	}
	count := 0
	for count < 500 {
		var i = <-ch
		count += i
	}
	t2 := time.Now().UnixNano()
	println("final ", float32((t2-t1)/1000000))
}

func httpGet() {
	//t3:=time.Now().UnixNano()
	resp, err := http.Get("http://www.google.com/")
	if err != nil {
		println(err.Error())
	}
	defer resp.Body.Close()
	//t4:=time.Now().UnixNano()
	ch <- 1
	//println(float32((t4-t3)/ 1000000))
}

package main

import (
	"fmt"
	"net/http"
	"os"

	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/controller"
	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/model"
	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/router"
)

func main() {

	check := func(err error) {
		if err != nil {
			panic(err)
		}
	}

	data, err := os.ReadFile("../../assets/gopher.json")
	check(err)

	story, err := model.JsonStory(data)
	check(err)

	storyHandler := controller.NewHandler(story)
	mux := router.New(&storyHandler)

	fmt.Println("Starting server on 8080")
	http.ListenAndServe(":8080", mux)

}

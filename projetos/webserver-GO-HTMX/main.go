package main

import (
	"html/template"
	"log"
	"net/http"
	"time"
)

type Filme struct {
	Titulo  string
	Diretor string
}

func check(err error) {
	if err != nil {
		log.Fatal("Ocorreu um erro:", err)
	}
}

func home(w http.ResponseWriter, r *http.Request) {
	tmpl := template.Must(template.ParseFiles("index.html"))

	filmes := map[string][]Filme{
		"filmes": {
			{Titulo: "The Godfather", Diretor: "Francis Ford Coppola"},
			{Titulo: "Blade Runner", Diretor: "Ridley Scott"},
			{Titulo: "The Thing", Diretor: "John Carpenter"},
		},
	}

	tmpl.Execute(w, filmes) //executa o template e joga no w (response) e jogamos dados adicionais (filmes)
}

func addFilme(w http.ResponseWriter, r *http.Request) {
	time.Sleep(1 * time.Second) //simulando espera no processamento da requisição
	log.Print("HTMX request received")
	log.Print(r.Header.Get("HX-Request"))

	titulo := r.PostFormValue("title")
	diretor := r.PostFormValue("director")

	tmpl := template.Must(template.ParseFiles("index.html"))
	//injetando o bloco HTML com os dados novos, sem precisar adicionar mais HTML
	//utiliza o bloco demarcado no index.html
	tmpl.ExecuteTemplate(w, "film-list-element", Filme{Titulo: titulo, Diretor: diretor})
}

func main() {
	http.HandleFunc("/", home)
	http.HandleFunc("/add-filme/", addFilme)
	check(http.ListenAndServe(":8080", nil))
}

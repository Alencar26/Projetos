package view

import "html/template"

var templates = []string{
	"../../templates/story.html",
	"../../templates/index.html",
}

func RenderTemplate() *template.Template {
	return template.Must(template.New("index.html").ParseFiles(templates...))
}

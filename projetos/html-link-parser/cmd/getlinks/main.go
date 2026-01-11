package main

import (
	"os"
	"strings"

	fl "github.com/Alencar26/Projetos/projetos/html-link-parser/pkg/findlink"
	"golang.org/x/net/html"
)

func main() {

	file, err := os.ReadFile("../../assets/index.html")
	if err != nil {
		panic(err)
	}

	r := strings.NewReader(string(file))

	doc, err := html.Parse(r)
	if err != nil {
		panic(err)
	}

	var links []fl.Link
	fl.Walk(doc, &links)

	fl.WriteMarkdown(links, "../../output/links.md")
}

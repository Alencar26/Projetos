package findlink

import (
	"fmt"
	"os"
	"strings"

	"golang.org/x/net/html"
)

type Link struct {
	Href string
	Text string
}

func Walk(n *html.Node, links *[]Link) {

	if isLink(n) {
		link := Link{
			Href: getHref(n),
			Text: getText(n),
		}
		*links = append(*links, link)
	}

	for c := n.FirstChild; c != nil; c = c.NextSibling {
		Walk(c, links)
	}
}

func isLink(n *html.Node) bool {
	if n.Type == html.ElementNode && n.Data == "a" {
		return true
	}
	return false
}

func getText(n *html.Node) string {

	var txt string

	if n.Type == html.TextNode {
		txt += n.Data + " "
	}
	if n.Type == html.ElementNode {
		for c := n.FirstChild; c != nil; c = c.NextSibling {
			txt += getText(c)
		}
	}
	return strings.TrimSpace(txt)
}

func getHref(n *html.Node) string {
	if n.Type == html.ElementNode && n.Data == "a" {
		for _, attr := range n.Attr {
			if attr.Key == "href" {
				return attr.Val
			}
		}
	}
	return ""
}

func (l Link) Markdown() string {
	return fmt.Sprintf("[%s](%s)", l.Text, l.Href)
}

func WriteMarkdown(links []Link, filename string) error {
	file, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer file.Close()

	for _, link := range links {
		_, err := file.WriteString(link.Markdown() + "\n\n")
		if err != nil {
			return err
		}
	}
	return nil
}

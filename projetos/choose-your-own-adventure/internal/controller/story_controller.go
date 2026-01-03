package controller

import (
	"html/template"
	"log"
	"net/http"
	"strings"

	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/model"
	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/view"
)

type Handler struct {
	s        model.Story
	t        *template.Template
	pathFunc func(r *http.Request) string
}

type HandlerOptions func(h *Handler)

func WithPathFunc(fn func(r *http.Request) string) HandlerOptions {
	return func(h *Handler) {
		h.pathFunc = fn
	}
}

func WithTemplate(t *template.Template) HandlerOptions {
	return func(h *Handler) {
		h.t = t
	}
}

var tmp = view.RenderTemplate()

func NewHandler(s model.Story, opts ...HandlerOptions) Handler {
	h := Handler{s: s, t: tmp, pathFunc: defaultPathFunc}
	for _, opt := range opts {
		opt(&h)
	}
	return h
}

func defaultPathFunc(r *http.Request) string {
	path := strings.TrimSpace(r.URL.Path)
	if path == "" || path == "/" {
		return "/intro"
	}
	return path[1:] //ignora o primeiro caracter (no nosso caso "/")
}

func (h *Handler) Index(w http.ResponseWriter, r *http.Request) {
	http.Redirect(w, r, defaultPathFunc(r), http.StatusFound)
}

func (h *Handler) ServerHTTP(w http.ResponseWriter, r *http.Request) {

	path := h.pathFunc(r)

	if chapter, ok := h.s[path]; ok {
		if err := h.t.Execute(w, chapter); err != nil {
			log.Printf("error: %v", err)
			http.Error(w, "Algo deu errado: "+err.Error(), http.StatusInternalServerError)
		}
		return
	}
	http.Error(w, "Capítulo não encontrado", http.StatusNotFound)
}

func (h *Handler) Favicon(w http.ResponseWriter, r *http.Request) {
	http.ServeFile(w, r, "favicon.ico")
}

package router

import (
	"net/http"

	"github.com/Alencar26/Projetos/projetos/choose-your-own-adventure/internal/controller"
)

func New(controller *controller.Handler) *http.ServeMux {
	mux := http.NewServeMux()

	// mux.HandleFunc("/", controller.Index)
	mux.HandleFunc("/", controller.ServerHTTP)
	mux.HandleFunc("/favicon.ico", controller.Favicon)

	return mux
}

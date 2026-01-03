package model

import (
	"encoding/json"
)

type Story map[string]Chapter

type Chapter struct {
	Title      string   `json:"title"`
	Paragraphs []string `json:"story"`
	Options    []Option `json:"options"`
}

type Option struct {
	Text    string `json:"text"`
	Chapter string `json:"arc"`
}

func JsonStory(jsonByte []byte) (Story, error) {
	var story Story
	if err := json.Unmarshal(jsonByte, &story); err != nil {
		return nil, err
	}
	return story, nil
}

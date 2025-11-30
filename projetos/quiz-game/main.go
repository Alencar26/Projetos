package main

import (
	"bufio"
	"encoding/csv"
	"errors"
	"flag"
	"fmt"
	"os"
	"regexp"
	"strconv"
	"strings"
	"time"
)

type Quiz struct {
	question string
	answer   string
	hit      bool
}

func main() {

	seconds := flag.Int("timer", 30, "timer in seconds")
	path := flag.String("path", "problems.csv", "file path")

	flag.Parse()

	forever := make(chan bool)
	answerCh := make(chan string)
	answered := 0
	hits := 0

	go Timer(*seconds, forever)

	quizzes, err := newQuiz(readCSV(*path))
	if err != nil {
		panic(err)
	}

	for i, quiz := range quizzes {
		fmt.Printf("Questão %d: %s = ", i+1, quiz.question)

		go func() {
			answerCh <- readInput()
			answered++
		}()

		select {
		case <-forever:
			for _, quiz := range quizzes {
				if quiz.hit {
					hits++
				}
			}
			fmt.Printf("\nVocê acertou %d!\n", hits)
			fmt.Printf("\nVocê respondeu %d de %d questões antes do tempo acabar!\n", answered, len(quizzes))
			return

		case userAnswer := <-answerCh:
			equationElements, err := elements(quiz.question)
			if err != nil {
				fmt.Println("Erro ao analisar a equação:", err)
				continue
			}
			correctAnswer := resultEquation(equationElements)
			correctAnswerStr := strconv.Itoa(correctAnswer)

			if compareAnswers(userAnswer, correctAnswerStr) {
				quiz.hit = true
			}
		}
	}
}

func Timer(seconds int, forever chan<- bool) chan bool {
	now := 0
	for {
		if now == seconds {
			fmt.Println("\nTempo esgotado!")
			forever <- true
		}
		now++
		time.Sleep(1 * time.Second)
	}
}

func newQuiz(records [][]string, err error) ([]*Quiz, error) {
	if err != nil {
		return nil, err
	}
	var quizzes []*Quiz
	for _, record := range records {
		quiz := &Quiz{
			question: record[0],
			answer:   record[1],
			hit:      false,
		}
		quizzes = append(quizzes, quiz)
	}
	return quizzes, nil
}

func compareAnswers(userAnswer string, correctAnswer string) bool {
	return userAnswer == correctAnswer
}

func readCSV(path string) (records [][]string, err error) {

	file, err := os.Open(path)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	r := csv.NewReader(file)
	r.Comma = ','
	r.Comment = '#'

	records, err = r.ReadAll()

	return records, err
}

func resultEquation(equation []string) int {
	switch equation[2] {
	case "+":
		x, _ := strconv.Atoi(equation[1])
		y, _ := strconv.Atoi(equation[3])
		return x + y
	case "-":
		x, _ := strconv.Atoi(equation[1])
		y, _ := strconv.Atoi(equation[3])
		return x - y
	case "*":
		x, _ := strconv.Atoi(equation[1])
		y, _ := strconv.Atoi(equation[3])
		return x * y
	case "/":
		x, _ := strconv.Atoi(equation[1])
		y, _ := strconv.Atoi(equation[3])
		return x / y
	default:
		return 0
	}
}

func elements(equation string) ([]string, error) {

	regex := regexp.MustCompile(`(\d+)\s*([+\-*/])\s*(\d+)`)
	match := regex.FindStringSubmatch(equation)

	if len(match) == 0 {
		return nil, errors.New("equação inválida")
	}

	return match, nil
}

func readInput() string {
	reader := bufio.NewReader(os.Stdin)
	input, _ := reader.ReadString('\n')
	input = strings.TrimSpace(input)
	return input
}

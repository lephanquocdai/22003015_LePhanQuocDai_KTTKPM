FROM golang:1.20

WORKDIR /app
COPY . .

RUN go mod init baitap05 || true
RUN go mod tidy

RUN go build -o app

EXPOSE 8080
CMD ["./app"]
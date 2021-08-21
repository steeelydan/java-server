sigint_handler()
{
  kill $PID
  exit
}

trap sigint_handler SIGINT

while true; do
  ./gradlew run &
  PID=$!
  inotifywait -e modify -e move -e create -e delete -e attrib -r ./src
  kill $PID
done

i = 0
sm = 0
while i < 10 {
  sm = sm + i
  i = i + 1
}

if sm % 2 == 0 {
  sm = sm + 10
} else {
  sm = sm + 20
}

sm
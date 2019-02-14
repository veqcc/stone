class Position {
  x = y = 0
  def move(nx, ny) {
    x = nx; y = ny
  }
}

class Pos3D extends Position {
  z = 0
  def set(nx, ny, nz) {
    x = nx; y = ny; z = nz
  }
}

p = Position.new
p.move(3,4)
p.x = 10
print "p.x + p.y = " + ( p.x + p.y )

q = Pos3D.new
q.move(5,6)
print "q.x = " + q.x
q.set(7,8,9)
print "q.z = " + q.z

import logging
logging.basicConfig(level=logging.DEBUG)

from pwrcall import loop, unloop, Node, expose

class Math(object):
	@expose
	def add(self, a, b):
		return a+b

	@expose
	def mul(self, a, b):
		return a*b

n = Node(cert='cert2.pem')
ref = n.register_object(Math())
n.listen(port=10000)
print 'math obj ready at', n.refurl(ref)

loop()


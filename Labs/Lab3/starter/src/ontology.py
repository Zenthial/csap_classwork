
class Concept:
	_slots__=['name','children', 'parent']
	def __init__(self, name, parent = None, children = None):
		if children == None:
			children=set()
		self.name = name
		self.parent= parent
		self.children = children	

	def getPathToTop(self)->list[Concept]:
		"""
		:return: A list representing the path between this Concept and the top of the Ontology
		"""
		ret = []
		curr = self
		while curr:
			ret.append(curr)
			curr = curr.parent
		return ret

	def _addChild(self,child):
		self.children.add(child)
		assert child.parent == None
		child.parent = self
					
	def __eq__(self, other):
		if type(other) != type(self): 
			return False
		return self.name == other.name 
		
	def __hash__(self):
		return hash(self.name)
		
	def __str__(self):
		ret = self.name
		return ret	
	def __repr__(self): #this is not a standard use of repr but it will help with printing lists (https://docs.python.org/3.4/library/functions.html?highlight=repr#repr)
		return str(self)

class Ontology:
	_slots__=['lookup','subsumes']
	def __init__(self, fn =None):
		self.lookup={} #name:concept
		self.subsumptionMap={} #c1:{x | subsumes(c1,x)}. Both are Concepts, not strings
		if fn !=None:
			self._proccessFile(fn)
			self._reason()
	def getAllConcepts(self)->list[Concept]:
		"""
		:return: A list of Concepts in the ontology
		"""
		ret =[]
		for x in self.lookup.values():
			ret.append(x)
		return ret
	def _getOrAddConceptFromString(self,conceptStr):
		if conceptStr not in self.lookup:
			self.lookup[conceptStr] = Concept(conceptStr)
		return self.lookup[conceptStr]
		
	def getConcept(self,conceptStr :str) -> Concept:
		"""
		:param conceptStr:
		:return: a Concept named conceptStr if it exists
		"""
		return self.lookup[conceptStr]	
		
	
	def _proccessFile(self, fn :str ):
		"""
		Reads an ontology file and adds the concepts to this object
		:param fn: a string specifying the file to load
		"""
		with open(fn) as f:
			for l in f:
				l = l.strip()
				conceptList = l.split(" ")
				parentStr = conceptList[0]
				childrenStrs=conceptList[1:]
				parent= self._getOrAddConceptFromString(parentStr)
				#print(parent)
				for c in childrenStrs:
				#	print("\t"+c)
					parent._addChild(self._getOrAddConceptFromString(c))


	def subsumes(self,c1 : Concept,c2 :Concept ) -> bool:
		"""
		Tests if c1 subsumes c2
		:param c1: a Concept, possible subsumer
		:param c1: a Concept, possible subsumed
		:return: True iff c1 subsumes c2; False otherwise
		"""
		return c2 in self.subsumptionMap[c1]


			
	def _reason(self):
		"""
		Builds and caches the subsumption map for O(1) lookup
		"""
		#n to find root
		for x in self.lookup.values():
			if x.parent==None:
		 		root = x
		 		break
		self._reasonRec(root)		
	
	def _reasonRec(self,root): #Recursive DFS
		ret = set()
		ret.add(root)
		for c in root.children:
			ret.update(self._reasonRec(c))
		self.subsumptionMap[root] = ret
		return ret
			


def _test(fn):
	o = Ontology()
	o._proccessFile(fn)		
	testConcepts=[]
	testConcepts.append( o.getConcept("dog"))
	testConcepts.append( o.getConcept("car"))
	testConcepts.append( o.getConcept("animal"))
	testConcepts.append( o.getConcept("physical-object"))
	testConcepts.append( o.getConcept("artifact"))

	#path to top
	for c in testConcepts:
		print(c, end=": ")
		for p in c.getPathToTop():
			print(p, end = " ")
		print()
	o._reason()			


def _main(fn):
	_test(fn)
if __name__ =="__main__":
	import sys
	_main(sys.argv[1])

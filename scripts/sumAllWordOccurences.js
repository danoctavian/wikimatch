var s = 0
print("sum of all word occurences in wc collection (all words in our examples)")
db.wc.find().forEach(function(d) {
  s += d.value
})

print(s)

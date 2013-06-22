db.train.mapReduce(
function() {
 function mapw(o) {
  function getWords(txt) {
    var words = txt.split(new RegExp("\\s+"))
    return words.map(function(w) {
      return w.toLowerCase().replace(/'s/g, "").replace(/[^a-zA-Z]/g, "")
    })
  }
  var ws1 = getWords(o.booktxt)
  var ws2 = getWords(o.wikisum)
  var ws3 = getWords(o.term)
  return ws1.concat(ws2).concat(ws3)
}



  mapw(this).forEach(function(w) { if (w !== "") {emit(w, 1)}})
},
function(k, vs) {
  var s = 0
  for (var i = 0; i < vs.length; i++) {
    s += vs[i]      
  }
  return s
},
{out: "wc"}
);

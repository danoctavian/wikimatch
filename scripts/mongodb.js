

var obj = { "_id" : "51c526da44ae4d90c0e88ad4", "wikiquery" : "Health Information", "wikititle" : "Health_informatics", "overlap" : 12, "class" : 1, "guid" : "f56a82f5", "booktxt" : "Any information whether oral or recorded in any form or medium that a) is created or received by a health care provider health plan public health authority employer life insurer school or university or health care clearinghouse and; b) relates to the past present or future physical or mental health or condition of an individual the provision of health care to an individual or the past present or future payment for the provision of health care to an individual.", "term" : "Health Information", "asin" : "B006QGPDEU", "wikisum" : "Health informatics (also called Health Information Systems health care informatics healthcare informatics medical informatics nursing informatics clinical informatics or biomedical informatics) is a discipline at the intersection of information science computer science and health care. It deals with the resources devices and methods required to optimize the acquisition storage retrieval and use of information in health and biomedicine. Health informatics tools include computers clinical guidelines formal medical terminologies and information and communication systems. It is applied to the areas of nursing clinical care dentistry pharmacy public health occupational therapy and (bio)medical research." }


function emit(k, v) {
  console.log("key|"  + k + "|value:" + v)
}

/* map reduce word count*/
function map(o) {
  function getWords(txt) {
    var words = txt.split(new RegExp("\\s+"))
    return words.map(function(w) {
      return w.toLowerCase().replace(/'s/g, "").replace(/[^a-zA-Z]/g, "")
    })
  }
  var ws1 = getWords(o.booktxt)
  var ws2 = getWords(o.wikisum)
  var ws3 = getWords(o.term)
  ws1.concat(ws2).concat(ws3).forEach(function(w) {
    emit(w, 1)
  })
}

var txt = "Position refers to the spatial location (rather than orientation) of an entity. The term may refer to any of the following below"


//console.log(getWords(txt))

map(obj)

function reduce(key, values) { 
  return values.reduce(function(x, y) {return x + y})
}


out: {replace: "wordCount"}

/*
db.collection.mapReduce(
                         <map>,
                         <reduce>,
                         {
                           out: <collection>,
                           query: <document>,
                           sort: <document>,
                           limit: <number>,
                           finalize: <function>,
                           scope: <document>,
                           jsMode: <boolean>,
                           verbose: <boolean>
                         }
                       )

*/

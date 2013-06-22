var docs = db.wc.find()

for (var i = 0; i < 500; i++) {
 print(docs[i]["_id"] + ":" + docs[i].value)
}

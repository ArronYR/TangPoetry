# tp_server

A [Sails](http://sailsjs.org) application for `Tang Poetry` API server.

## Install
```
git clone https://github.com/ArronYR/TangPoetry/
npm install
sails lift
```

## API
### count

+ `http://localhost:1337/poet/count`
```json
{
  "status": 200,
  "msg": "success",
  "count": 2528
}
```
+ `http://localhost:1337/poetry/count`
```json
{
  "status": 200,
  "msg": "success",
  "count": 43029
}
```

### rand
+ `http://localhost:1337/poet/rand`
```json
{
  "status": 200,
  "msg": "success",
  "poet": {
    "poetries": [
      {
        "id": 9178,
        "title": "寄妻父刘长卿（一作严维诗，题作发桐庐寄刘员外）",
        "content": "处处云山无尽时，桐庐南望转参差。舟人莫道新安近，欲上潺湲行自迟。",
        "created_at": "2014-06-02T03:59:43.000Z",
        "updated_at": "2014-06-02T03:59:43.000Z",
        "poet": 677
      }
    ],
    "id": 677,
    "name": "李穆",
    "created_at": "2014-06-02T03:59:43.000Z",
    "updated_at": "2014-06-02T03:59:43.000Z"
  }
}
```
+ `http://localhost:1337/poetry/rand`
```json
{
  "status": 200,
  "msg": "success",
  "poetry": {
    "poet": {
      "id": 132,
      "name": "杜甫",
      "created_at": "2014-06-02T03:49:07.000Z",
      "updated_at": "2014-06-02T03:49:07.000Z"
    },
    "id": 10275,
    "title": "送赵十七明府之县",
    "content": "连城为宝重，茂宰得才新。山雉迎舟楫，江花报邑人。论交翻恨晚，卧病却愁春。惠爱南翁悦，馀波及老身。",
    "created_at": "2014-06-02T04:01:08.000Z",
    "updated_at": "2014-06-02T04:01:08.000Z"
  }
}
```

### Find

+ `http://localhost:1337/poet`
+ `http://localhost:1337/poetry`

Request these two API will return `10` records about `poet` or `poetry`.

You can also sent parameters in the request.Like these:

+ To filter results based on a particular attribute, specify a query parameter with the same name as the attribute defined on your model.
```json
e.g.?id=1
e.g.?name=杜甫
```
+ This allows you to take advantage of contains, startsWith, and other sub-attribute criteria modifiers for more powerful find() queries.
```json
e.g.?where={"name":{"contains":"李"}}
```
+ The maximum number of records to send back (useful for pagination). Defaults to 10.
```json
e.g.?limit=100
```
+ The number of records to skip (useful for pagination).
```json
e.g.?skip=30
```
+ The sort order. By default, returned records are sorted by primary key value in ascending order.
```json
e.g.?sort=name ASC
```
+ If specified, overide the default automatic population process. Accepts a comma separated list of attributes names for which to populate record values.
```json
e.g.?populate=poetries
```
+ If specified, a JSONP response will be sent (instead of JSON). This is the name of a client-side javascript function to call, to which results will be passed as the first (and only) argument.
```json
e.g.?callback=my_JSONP_data_receiver_fn
```

More infos see [here](http://sailsjs.org/documentation/reference/blueprint-api/find-where).

## About me

博客：[http://blog.helloarron.com](http://blog.helloarron.com)
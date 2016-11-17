
var express = require('express');
var app = express();
var request = require('request');
app.get('/getdata/:id', function (req, res) {
    var result={
        'body': []
    };

    request('https://restcountries.eu/rest/v1/name/'+req.params.id, function (error,response,body) {
        if(error){
            return console.log('Error:', error);
        }

        if(response.statusCode !== 200){
            return console.log('Invalid Status Code Returned:', response.statusCode);
        }
        body = JSON.parse(body);
        conti = body;

            lat = conti[0].latlng[0];
            lang = conti[0].latlng[1];

        request('http://api.openweathermap.org/data/2.5/weather?lat='+lat+'&lon='+lang+'&appid=a38b8686ab2b84d771097ddc188896e5&units=metric', function (error, response, body1) {

            if (error) {
                return console.log('Error:', error);
            }

            if (response.statusCode !== 200) {
                return console.log('Invalid Status Code Returned:', response.statusCode);
            }

            body1 = JSON.parse(body1);
            weath = body1;

                result.body.push({"name": conti[0].name,"capital": conti[0].capital,"region": conti[0].region,"population": conti[0].population,"weather":weath.weather[0].description,"temp":weath.main.temp,"press":weath.main.pressure,"humidity":weath.main.humidity,"currencies": conti[0].currencies[0]});

            res.contentType('application/json');
            res.write(JSON.stringify(result));
            res.end();

        });
    });

})
var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port

})
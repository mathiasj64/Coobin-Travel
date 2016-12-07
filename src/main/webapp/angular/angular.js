angular.module("CoobinApp", [])

//Main controller to handle the 'search' part of the system.
    .controller("mainController", ["$scope", "airlineFactory", function ($scope, airlineFactory) {
        $scope.airlineFactory = airlineFactory;

    }])


    //Handles all the http requests to the different airline API's.
    .factory("airlineFactory", ["$http", function ($http) {
        var f = {
            airlines: []
        };

        //BRUGES TIL AT TESTE API
        f.getFlightTest = function () {
            console.log("get Flight");
            return $http.get("/api/flightinfo/SXF/2017-12-31T00:00:00.000Z/1")
                .success(function (data) {
                    console.log("Success! (get flight from)");
                    //clear list before adding flights
                    f.airlines.splice(0, f.airlines.length);
                    //Angular copy creates a deep copy of source, which should be an object or an array
                    //That object/array cannot be changed.
                    angular.copy(data, f.airlines);
                });
        };

        //FROM: Start Aiport (as an IATA), TO: Destination Aiport (as an IATA Code),
        //DATE: Travel Date (ISO-8601 date), TICKETS: requested amount of tickets (integer),
        f.getFlights = function (from, to, date, tickets) {
            var year = date.getFullYear();
            var month = date.getMonth();
            var day = date.getDate();
            date = new Date(year, month, day, 1);

            if (to === null || to === undefined || to === "") {
                console.log("Searching for flights");
                return $http.get("/api/allairlines/" + from + "/" + date.toISOString() + "/" + tickets)
                    .success(function (data) {
                        console.log("Success! (get flight from)");
                        //clear list before adding flights
                        f.airlines.splice(0, f.airlines.length);

                        //Angular copy creates a deep copy of source, which should be an object or an array
                        //That object/array cannot be changed.
                        angular.copy(data, f.airlines);
                    });
            } else {
                console.log("Searching for flights");
                return $http.get("/api/allairlines/" + from + "/" + to + "/" + date.toISOString() + "/" + tickets)
                    .success(function (data) {
                        console.log("Success! get flight from to");
                        f.airlines.splice(0, f.airlines.length);
                        angular.copy(data, f.airlines);
                    });
            }
        };

        return f;
    }]);




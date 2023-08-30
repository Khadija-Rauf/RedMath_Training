(function () {
    'use strict';

    angular.module("news", ['ngResource', 'ng']);

    function NewsService($resource) {
        return $resource('api/v1/news/:id');
    }

    angular.module('news').factory('newsService', ['$resource', NewsService]);

    function NewsController(newsService) {
        var self = this;
    console.log('self.title:', self.title);
        self.service = newsService;
        self.news = [];
        self.title = '';
        self.display = false;

        self.init = function () {
            self.search();
        }

        self.search = function () {
            self.display = false;
            var parameters = {};
            if (self.title) {
                parameters.title = '%' + self.title + '%';
            } else {
                parameters.title = '%';
            }
            self.service.get(parameters).$promise.then(function (response) {
                self.display = true;
                self.news = response.content;
            });
        }

        self.init();
    }

    angular.module("news").controller('newsController', ['newsService', NewsController]);
})();

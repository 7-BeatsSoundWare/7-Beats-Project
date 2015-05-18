/* 
 * @author Augusto César
 */

var app = angular.module('7BeatsDirectives', []);

app.directive('albumCard', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: '/webapp/templates/album-card.html',
        scope: false
    };
});

app.directive('cardMusicList', function(){
    return {
        restrict: 'E',
        scope: false,
        templateUrl: '/webapp/templates/card-music-list.html'
    };
});

app.directive('loader', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: '/webapp/templates/loader.html'
    };
});
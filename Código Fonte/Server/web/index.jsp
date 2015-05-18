<%-- 
    Document   : index
    Created on : May 6, 2015, 3:27:38 PM
    Author     : Augusto Cesar
--%>

<%@page contentType="text/html"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="shortcut icon" href="/resources/stream.ico"/>

        <link rel="stylesheet" href="/resources/lib/materialize/css/materialize.min.css"/>

        <script src="/resources/lib/materialize/js/jquery.js"></script>
        <script src="/resources/lib/materialize/js/materialize.min.js"></script>
        <script src="/resources/lib/angular/angular.min.js"></script>
        <script src="/resources/lib/angular-soundmanager2.min.js"></script>

        <script src="/webapp/directives.js"></script>

        <style>
            #player{
                width: 90%; 
                height: 80px; 
                position: fixed; 
                bottom: 15px; 
                left: 5%; 
                z-index: 10000;
                border-radius: 20px;
            }
            
            .list-item-disabled :hover{
                background-color: transparent !important;
            }
            
            .img-profile{
                width: 35px; 
                height: 35px;
                border-radius: 50px;
            }
        </style>
        <title>7 Beats</title>
    </head>
    <body ng-app="7Beats">    
        <nav class="navbar-fixed">
            <div class="nav-wrapper blue lighten-2">
                <a href="#" class="brand-logo">7 Beats</a>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li>
                        <a data-target="modal1" class="modal-trigger" href="">Entrar ou Cadastrar</a>
                    </li>
                    <!--<li>
                        <a href="" class="list-item-disabled">                            
                            Augusto Cesar
                            <img class="img-profile" src="http://69.28.84.155/public/users_profile/profile_augustoccesar.jpg"/>
                        </a> 
                    </li>-->
                </ul>
            </div>
        </nav>  

        <div ng-controller="7BeatsController" ng-init="getAlbuns();">
            <!-- Login Modal -->            
            <div id="modal1" class="modal bottom-sheet">
                <div class="modal-content">
                    <div class="row">
                        <div class="col l12 m12 s12">
                            <h4>Login</h4>
                        </div>
                    </div>
                    <div class="row">
                        <form class="col l12 m12 s12">
                            <div class="row">
                                <div class="input-field col l12 m12 s12">
                                    <input type="text" ng-model="credentials.username"/>
                                    <label>Usuário</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col l12 m12 s12">
                                    <input type="password" ng-model="credentials.senha"/>
                                    <label>Senha</label>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field col l12 m12 s12">
                                    <button class="btn blue" ng-click="doLogin()">Entrar</button>
                                </div>
                            </div>
                        </form>
                    </div>                    
                </div>
            </div>
            <!-- ************* -->
            
            <div class="row center-align" ng-if="loadingAlbuns">
                <loader></loader>
            </div>
            <div class="row">
                <div ng-repeat="album in albuns">
                    <album-card></album-card> 
                </div>
            </div>

            <div class="row">
                <card-music-list></card-music-list>
            </div>
        </div>     

        <div id="player" class="blue" ng-show="currentPlaying">
            <sound-manager></sound-manager>
            <div class="row">
                <div class="col l2 s2">                    
                    <a ng-show="!isPlaying" play-music class="btn-floating waves-effect waves-light red" style="margin-top: 21px"><i class="mdi-av-play-arrow"></i></a>
                    <a ng-show="isPlaying" pause-music class="btn-floating waves-effect waves-light red" style="margin-top: 21px"><i class="mdi-av-pause"></i></a>
                </div>
                <div class="col l4 s4">
                    <h5>{{currentPlaying.nome}}</h5>
                </div>
                <div class="col l6 s6">
                    <div seek-track>
                        <p class="range-field">
                            <input style="border: none" type="range" value="{{progress}}" max="{{currentDuration}}" />
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <script>
            var app = angular.module("7Beats", [
                'angularSoundManager',
                '7BeatsDirectives'
            ]);

            app.controller('7BeatsController', function ($scope, $http, $location, $anchorScroll) {
                $scope.musicaAtual = {};
                $scope.credentials = {};

                $scope.getAlbuns = function () {
                    $scope.loadingAlbuns = true;
                    $http.get("rest/albuns").then(function (response) {
                        $scope.albuns = response.data;
                        $scope.loadingAlbuns = false;
                    });
                };

                $scope.getAlbum = function (id_album) {
                    $http.get("rest/albuns/id/" + id_album).then(function (response) {
                        $scope.albumSelecionado = response.data;

                        $location.hash('albumSelecionado');
                        $anchorScroll();
                    });
                };
                
                $scope.doLogin = function(){
                    $http({
                        method: 'POST',
                        url: "rest/usuarios/auth",
                        data: {username: $scope.credentials.username, senha: $scope.credentials.senha},
                        headers: {'Content-Type': 'application/json'}
                    }).then(function(response){
                        console.log(response);
                    });
                };

            });
        </script>
        <script>
            $(document).ready(function () {
                $('.modal-trigger').leanModal();
            });
        </script>
    </body>
</html>

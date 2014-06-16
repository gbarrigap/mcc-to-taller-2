<%-- 
    Document   : Pics
    Created on : Jun 11, 2014, 2:02:54 PM
    Author     : guillermo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="famipics.domain.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="PicBean" class="famipics.domain.Pic"></jsp:useBean>
    <!DOCTYPE html>
    <html>
        <head>
            <title>FamiPics</title>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <!-- jQuery -->
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
            <!-- BlueImp Gallery -->
            <script src="http://blueimp.github.io/Gallery/js/jquery.blueimp-gallery.min.js"></script>
            <script src="js/bootstrap-image-gallery.min.js"></script>

            <!-- Boostrap -->
            <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css" />
            <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.css" />
            <script src="lib/bootstrap/js/bootstrap.js"></script>
            <!-- BlueImp -->
            <link rel="stylesheet" href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
            <link rel="stylesheet" href="css/bootstrap-image-gallery.min.css">

            <!-- Custom CSS & JavaScript -->
            <link rel="stylesheet" href="css/screen.css" />
            <style>
                .blueimp-gallery > .description {
                    position: absolute;
                    top: 30px;
                    left: 15px;
                    color: #fff;
                    display: none;
                }
                .blueimp-gallery-controls > .description {
                    display: block;
                }
            </style>
            <script lang="javascript">
                $(function() {
                    blueimp.Gallery(
                            document.getElementById('links'), {
                        onslide: function(index, slide) {
                            var text = this.list[index].getAttribute('data-description'),
                                    node = this.container.find('.description');
                            node.empty();
                            if (text) {
                                node[0].appendChild(document.createTextNode(text));
                            }
                        }
                    });

                    document.getElementById('links').onclick = function(event) {
                        event = event || window.event;
                        var target = event.target || event.srcElement,
                                link = target.src ? target.parentNode : target,
                                options = {index: link, event: event, onslide: function(index, slide) {
                                        var text = this.list[index].getAttribute('data-description'),
                                                node = this.container.find('.description');
                                        node.empty();
                                        if (text) {
                                            node[0].appendChild(document.createTextNode(text));
                                        }
                                    }},
                        links = this.getElementsByTagName('a');
                        blueimp.Gallery(links, options);
                    };
                });
            </script>
        </head>
        <body>
        <c:import url="Header.jsp"></c:import>

            <!-- The Bootstrap Image Gallery lightbox, should be a child element of the document body -->
            <div id="blueimp-gallery" class="blueimp-gallery">
                <!-- The container for the modal slides -->
                <div class="slides"></div>
                <!-- Controls for the borderless lightbox -->
                <h3 class="title"></h3>
                <p class="description"></p>
                <a class="prev">‹</a>
                <a class="next">›</a>
                <a class="close">×</a>
                <a class="play-pause"></a>
                <ol class="indicator"></ol>
                <!-- The modal dialog, which will be used to wrap the lightbox content -->
                <div class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" aria-hidden="true">&times;</button>
                                <h4 class="modal-title"></h4>
                            </div>
                            <div class="modal-body next"></div>
                            <p class="description"></p>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default pull-left prev">
                                    <i class="glyphicon glyphicon-chevron-left"></i>
                                    Previous
                                </button>
                                <button type="button" class="btn btn-primary next">
                                    Next
                                    <i class="glyphicon glyphicon-chevron-right"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <p>${sessionScope.uploadResultMessage}</p>
        <c:remove var="uploadResultMessage" scope="session" />

        <div class="row">
            <c:forEach var="pic" items="${PicBean.all}">
                <div class="col-xs-4 col-md-3 col-lg-2">
                    <div class="thumbnail">
                        <a href="files/pics/${pic.filename}" class="thumbnail" data-gallery data-description="This is the description...">
                            <img src="files/pics/${pic.filename}" alt="" width="200px" />
                        </a>
                        <div class="caption">
                            <p>${pic.teaser}</p>
                            <p>
                                [<a href="Pic.jsp?pid=${pic.pid}">view</a>]
                                <c:if test="${sessionScope.currentUser.admin || sessionScope.currentUser.uid == pic.uid}">
                                    [<a href="">edit</a>]
                                    [<a href="">delete</a>]
                                </c:if>
                            </p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </body>
</html>

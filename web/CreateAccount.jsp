<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>FamiPics</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Boostrap -->
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" href="lib/bootstrap/css/bootstrap-theme.css" />
        <script src="lib/bootstrap/js/bootstrap.js"></script>

        <!-- Custom CSS & JavaScript -->
        <link rel="stylesheet" href="css/screen.css" />
    </head>
    <body>
        <div class="jumbotron">
            <h1>FamiPics</h1>
        </div>

        <div class="container">
            <form method="post" action="CreateAccount">
                <fieldset>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input id="email" name="email" type="text" class="form-control" />
                    </div>

                    <div class="form-group">
                        <label for="display-name">Display Name</label>
                        <input id="display-name" name="display-name" type="text" class="form-control" />
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" name="password" type="text" class="form-control" />
                    </div>

                    <div class="form-group">
                        <label for="password-confirmation">Password Confirmation</label>
                        <input id="password-confirmation" name="password-confirmation" type="text" class="form-control" />
                    </div>

                    <div class="form-group text-right">
                        <a href="#">I have an account</a>
                        <input type="submit" value="Create my account" class="btn btn-primary" />
                    </div>
                </fieldset>
            </form>
        </div>
    </body>
</html>

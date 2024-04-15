package org.example.route;

import akka.http.javadsl.server.Route;


import static akka.http.javadsl.model.StatusCodes.NO_CONTENT;
import static akka.http.javadsl.server.Directives.*;


public class StatusRoute {

    public final Route route;

    public StatusRoute() {

        route = path("status", () -> head(() ->
                complete(NO_CONTENT)));

    }
}
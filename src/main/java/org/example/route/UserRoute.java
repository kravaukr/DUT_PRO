package org.example.route;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.directives.RouteAdapter;
import org.example.model.User;
import org.example.service.UserService;
import akka.http.javadsl.server.Route;

import java.util.Optional;

import static akka.http.javadsl.server.Directives.*;

public class UserRoute extends AllDirectives {

    public final Route route;

    private final UserService userService;

    public UserRoute(UserService userService) {
        this.userService = userService;
        route = pathPrefix("api", () ->
                pathPrefix("users", () ->
                        concat(
                                post(() ->
                                        entity(Jackson.unmarshaller(User.class), userDto -> {
                                            boolean response = userService.createUser(userDto);
                                            if (response) return complete(StatusCodes.CREATED);
                                            else return complete(StatusCodes.BAD_REQUEST);
                                        })
                                ),
                                path(PathMatchers.segment(), login ->
                                        concat(
                                                get(() -> {
                                                    Optional<User> user = userService.getUser(login);
                                                    return user.map(u -> complete(StatusCodes.OK, u, Jackson.marshaller()))
                                                            .orElseGet(() -> (RouteAdapter) complete(StatusCodes.NOT_FOUND));
                                                }),
                                                delete(() -> {
                                                    userService.deleteUser(login);
                                                    return complete(StatusCodes.OK);
                                                })
                                        )
                                ),
                                put(() ->
                                        entity(Jackson.unmarshaller(User.class), user -> {
                                            User updatedUser = userService.updateUser(user);
                                            return complete(StatusCodes.OK, updatedUser, Jackson.marshaller());
                                        })
                                )
                        )
                )
        );
    }


//                concat(
//                pathPrefix(segment("api").slash("users") () ->
//                        concat(
//                                post(() ->
//                                        entity(Jackson.unmarshaller(User.class), user -> {
//                                            User newUser = userService.createUser(user);
//                                            return complete(StatusCodes.CREATED, newUser, Jackson.marshaller());
//                                        })
//                                ),
//                                get(() ->
//                                        path(PathMatchers.segment(), login -> {
//                                            Optional<User> user = userService.getUser(login);
//                                            return user.map(u -> complete(StatusCodes.OK, u, Jackson.marshaller()))
//                                                    .orElseGet(() -> (RouteAdapter) complete(StatusCodes.NOT_FOUND));
//                                        })
//                                ),
//                                put(() ->
//                                        entity(Jackson.unmarshaller(User.class), user -> {
//                                            User updatedUser = userService.updateUser(user);
//                                            return complete(StatusCodes.OK, updatedUser, Jackson.marshaller());
//                                        })
//                                ),
//                                delete(() ->
//                                        path(PathMatchers.segment(), login -> {
//                                            userService.deleteUser(login);
//                                            return complete(StatusCodes.NO_CONTENT);
//                                        })
//                                )
//                        )
//                )
//        );
//    }


}

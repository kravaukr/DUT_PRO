package org.example.route;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCode;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.directives.RouteAdapter;
import org.example.App;
import org.example.model.User;
import org.example.service.UserService;
import akka.http.javadsl.server.Route;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.UUID;

import static akka.http.javadsl.marshallers.jackson.Jackson.marshaller;
import static akka.http.javadsl.model.StatusCodes.ACCEPTED;
import static akka.http.javadsl.model.StatusCodes.NOT_FOUND;
import static akka.http.javadsl.model.headers.RawHeader.create;
import static akka.http.javadsl.server.Directives.*;
import static org.slf4j.LoggerFactory.getLogger;

public class UserRoute extends AllDirectives {

    private static final Logger log = getLogger(AllDirectives.class);

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
                                                    UUID cid = UUID.randomUUID();
                                                    log.info("[{}] OK, request accepted, login: {}", cid, login);
                                                    Optional<User> user = userService.getUser(login);
                                                    return user.map(u -> completeWithOk(u,cid)).orElseGet(() -> completeWithError(NOT_FOUND, cid));
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
    private Route completeWithOk(final User user, UUID cid) {

        log.info("[{}] OK, request processed successfully, code: {}, body: {}", cid, ACCEPTED.intValue(), user);

        return complete(StatusCodes.OK, user, Jackson.marshaller());
    }

    private Route completeWithError(final StatusCode statusCode, UUID cid) {

        log.info("[{}] FAIL, request processed with error, code: {}, body: {}", cid, statusCode.intValue(), statusCode.defaultMessage());

        return complete(StatusCodes.NOT_FOUND);
    }
}

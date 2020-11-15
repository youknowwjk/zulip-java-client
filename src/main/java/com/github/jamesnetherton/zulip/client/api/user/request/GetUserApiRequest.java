package com.github.jamesnetherton.zulip.client.api.user.request;

import static com.github.jamesnetherton.zulip.client.api.user.request.UserRequestConstants.USERS_WITH_ID;

import com.github.jamesnetherton.zulip.client.api.core.ExecutableApiRequest;
import com.github.jamesnetherton.zulip.client.api.core.ZulipApiRequest;
import com.github.jamesnetherton.zulip.client.api.user.User;
import com.github.jamesnetherton.zulip.client.api.user.response.GetUserApiResponse;
import com.github.jamesnetherton.zulip.client.exception.ZulipClientException;
import com.github.jamesnetherton.zulip.client.http.ZulipHttpClient;

/**
 * Zulip API request builder for getting a user.
 *
 * @see <a href="https://zulip.com/api/get-user">https://zulip.com/api/get-user</a>
 */
public class GetUserApiRequest extends ZulipApiRequest implements ExecutableApiRequest<User> {

    public static final String CLIENT_GRAVATAR = "client_gravatar";
    public static final String INCLUDE_CUSTOM_PROFILE_FIELDS = "include_custom_profile_fields";

    private final long userId;

    /**
     * Constructs a {@link GetUserApiRequest}.
     *
     * @param client The Zulip HTTP client
     * @param userId The id of the user to get
     */
    public GetUserApiRequest(ZulipHttpClient client, long userId) {
        super(client);
        this.userId = userId;
    }

    /**
     * Sets whether to include the user gravatar image URL in the response.
     *
     * @param  gravatar {@code true} to include the gravatar image URL in the response. {@code false} to not include the
     *                  gravatar image URL in the response
     * @return          This {@link GetUserApiRequest} instance
     */
    public GetUserApiRequest withClientGravatar(boolean gravatar) {
        putParam(CLIENT_GRAVATAR, gravatar);
        return this;
    }

    /**
     * Sets whether to include the user custom profile fields in the response.
     *
     * @param  includeCustomProfileFields {@code true} to include user custom profile fields in the response. {@code false} to
     *                                    not include user custom profile fields in the response
     * @return                            This {@link GetUserApiRequest} instance
     */
    public GetUserApiRequest withIncludeCustomProfileFields(boolean includeCustomProfileFields) {
        putParam(INCLUDE_CUSTOM_PROFILE_FIELDS, includeCustomProfileFields);
        return this;
    }

    /**
     * Executes the Zulip API request for getting a user.
     *
     * @return                      {@link User} object that matched the request user id
     * @throws ZulipClientException if the request was not successful
     */
    @Override
    public User execute() throws ZulipClientException {
        String path = String.format(USERS_WITH_ID, userId);
        GetUserApiResponse response = client().get(path, getParams(), GetUserApiResponse.class);
        return new User(response.getUserApiResponse());
    }
}

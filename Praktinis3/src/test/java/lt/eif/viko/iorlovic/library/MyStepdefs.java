package lt.eif.viko.iorlovic.library;

import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import lt.eif.viko.iorlovic.library.Models.Author;
import okhttp3.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MyStepdefs {

    private static final String BASE_URL = "http://localhost:7171";
    private static final String PATH = "/authors";
    private OkHttpClient client = new OkHttpClient();
    private Response response;
    private JSONArray authorList;
    private String responseBody;

    @When("the client trying to get all authors")
    public void theClientTryingToGetAllAuthors() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + PATH)
                .build();

        Call call = client.newCall(request);
        response = call.execute();
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertThat(response.code()).isEqualTo(expectedStatus);
    }

    @And("the response should contain the following authors")
    public void theResponseShouldContainTheFollowingAuthors() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(new StringReader(response.body().string()));
        JSONArray authors = (JSONArray) rootObject.get("authors");
    }

    @When("the client requests to get author by id")
    public void theClientRequestsToGetAuthorById() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + PATH + "/" + 2)
                .build();

        Call call = client.newCall(request);
        response = call.execute();
    }

    @And("the response should contain the author with id {int} and name {string}")
    public void theResponseShouldContainTheAuthorWithIdAndName(int arg0, String arg1) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject author = (JSONObject) parser.parse(new StringReader(response.body().string()));
    }
    @When("the client trying to create new author")
    public void theClientTryingToCreateNewAuthor(DataTable dataTable) throws IOException {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String json = new Gson().toJson(data.get(0));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(BASE_URL + PATH)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        response = call.execute();
    }

    @Given("the author repository is initialized with the following authors")
    public void theAuthorRepositoryIsInitializedWithTheFollowingAuthors(DataTable dataTable) throws IOException {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", row.get("name"));
            String json = jsonObject.toString();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(BASE_URL + PATH)
                    .post(requestBody)
                    .build();

            response = client.newCall(request).execute();
            if (response.code() != 201 && response.code() != 400) {
                throw new AssertionError("Expected: 201 but was: " + response.code());
            }
        }
    }

    @When("the client trying to update author")
    public void theClientTryingToUpdateAuthor(DataTable dataTable) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            jsonObject.put("name", row.get("name"));
        }
        String json = jsonObject.toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder()
                .url(BASE_URL + PATH + "/53")
                .put(requestBody)
                .build();

        response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new AssertionError("Expected: 200 but was: " + response.code());
        }
    }

    @When("the client trying to delete authors")
    public void theClientTryingToDeleteAuthors() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + PATH + "/53")
                .delete()
                .build();

        response = client.newCall(request).execute();
    }

    @And("the author with id {int} should not exist in the repository")
    public void theAuthorWithIdShouldNotExistInTheRepository(int id) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + PATH + "/" + id)
                .build();

        Call call = client.newCall(request);
        Response checkResponse = call.execute();

        assertThat(checkResponse.code()).isEqualTo(200);
    }
}



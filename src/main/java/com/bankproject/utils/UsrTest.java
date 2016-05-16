package com.bankproject.utils;

/**
 * Created by bobyk on 16/05/16.
 */
public class UsrTest {

    /*@Test
    public void testBookByTitle() {
        BookRequest request = new BookRequest();
        request.setTitle("East of Eden");

        Entity entity = Entity.json(request);

        Client client = createClient();

        WebTarget target = client
                .target("http://localhost:8080/rest-service/rest/book/by_title");

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(entity);

        Book book = response.readEntity(Book.class);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(2, book.getId());
        assertEquals("John Steinbeck", book.getAuthor());
        assertEquals(59, book.getPrice());
    }*/

    /*Client createClient() {
        return ClientBuilder
                .newBuilder()
                .register(JacksonJaxbJsonProvider.class)
                .build();
    }*/

}

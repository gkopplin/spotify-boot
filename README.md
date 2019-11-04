# spotify-boot API
___Grant Kopplin & Nate Tuvera___

 A Spring Boot Spotify clone API for Adding Users, Songs, and Songs to Users

### Technologies Used:
- Spring Boot Framework
  - Spring Security
  - Spring Testing
  - Spring Web
- J-Unit
  - Mockito
- PostgresQL

### Design Decisions:
We started simply with user CRUD and then implemented adding relations to songs.  Since they can be independent entities, but are in a mony-to-many relationship, it was easier to visualize and code them independently.  After each entity was functioning on it's own we added the correct routes/services/methods to tie in the relational mapping (aka the join table).

### What went right:
Overall it was a godo pair programming experience.  We added a persistence check which involved optionals that lead to a better understanding of how to user optionals and the scopes they traverse (source code and test code).

### Challenges Faced:
While dealing with deleting and adding users we ran into an issue with authorizations either being completely locked down or completely open.  The solution was to clear the session on the server as it was stored with each connection made.  The stored session also stored the authorization access level, which should be different per a user based on their user role.

### Which part you enjoyed working on the most:
The pair programming went well and the re-enforcement of a simpler CRUD app was extremely helpful.  The layer of spring boot ontop was less of a distraction and helped move things ahead quickly.  The only caveat was learning how repositories worked and using new Annotations.

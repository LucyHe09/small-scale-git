# small-scale-git

A simplified version control system implemented in Java using linked lists. This project emulates basic Git-like functionality, allowing users to create and manage commit histories, synchronize repositories, and explore version control logic at a structural level.

## Features

- Create and manage repositories
- Add commits with custom messages
- Track commit history with timestamps
- View repository head and size
- Search for commits by ID
- Drop specific commits from the history
- Synchronize two repositories by merging their histories in timestamp order

## Class Structure

```plaintext
Repository
    └── Commit (static inner class)
Client.java
Testing.java
```


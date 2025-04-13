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

## Class Breakdown

### `Repository.java`
Core class representing a local repository of commits. Implements:
- `commit(String message)` — creates and adds a new commit to the repository
- `drop(String targetId)` — removes a commit while maintaining history integrity
- `synchronize(Repository other)` — merges another repository’s history in timestamp order
- `getRepoHead()` — returns the ID of the latest commit
- `getRepoSize()` — returns the total number of commits
- `contains(String targetId)` — checks if a commit exists
- `getHistory(int n)` — returns the most recent n commits
- `toString()` — summary view of the repository's current state

### `Commit` (static inner class in `Repository.java`)
Defines individual commits. Contains:
- `id`, `message`, and `timestamp` — immutable fields for tracking
- `previous` — reference to the preceding commit
- Auto-generated unique IDs and human-readable timestamps
- Used internally for constructing linked commit history

### `Client.java`
Demonstrates usage of the repository system. Can:
- Create and name new repositories
- Add commits with custom messages
- Drop specific commits
- Print repository history and head
- Synchronize two repositories

### `Testing.java`
Unit tests for all major methods, especially:
- `synchronize()` with front, middle, end, and empty merge cases
- Ensures ordering and integrity of commit history
- Validates edge cases and exception handling
- Uses `Thread.sleep()` to differentiate commit timestamps



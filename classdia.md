# Library Management System - Class Diagram

## Page 1: UI Layer

```
┌─────────────────────────────────────────────────────────────────────┐
│                         Library Management System                    │
└─────────────────────────────────────────────────────────────────────┘

┌──────────────────┐
│      Main        │
├──────────────────┤
│ +main(String[])  │
└──────┬───────────┘
       │
       ▼
┌──────────────────────────────────────────────────────────────────┐
│                          UI LAYER                                 │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────────┐    ┌──────────────────────────┐  │
│  │  RoleSelectionFrame      │    │   AdminLoginFrame        │  │
│  ├──────────────────────────┤    ├──────────────────────────┤  │
│  │ -studentBtn              │    │ -userField               │  │
│  │ -adminBtn                │    │ -passField               │  │
│  ├──────────────────────────┤    ├──────────────────────────┤  │
│  │ +RoleSelectionFrame()    │    │ +AdminLoginFrame()       │  │
│  │ -styleButton()           │    │ -styleButton()           │  │
│  └──────────────────────────┘    │ -onLogin()               │  │
│                                   └──────────┬───────────────┘  │
│                                              │                  │
│  ┌──────────────────────────┐               │                  │
│  │  StudentLoginFrame       │               │                  │
│  ├──────────────────────────┤               │                  │
│  │ -regField                │               │                  │
│  │ -nameField               │               │                  │
│  ├──────────────────────────┤               │                  │
│  │ +StudentLoginFrame()     │               │                  │
│  │ -styleButton()           │               │                  │
│  │ -onLogin()               │               │                  │
│  └──────────┬───────────────┘               │                  │
│             │                               │                  │
│             │       ┌────────────────────────▼──────────────┐  │
│             │       │    AdminDashboardFrame               │  │
│             │       ├──────────────────────────────┐       │  │
│             ▼       │                              │       │  │
│  ┌──────────────────▼──────┐  ┌────────────────────▼──┐   │  │
│  │ StudentDashboardFrame   │  │ -borrowTableModel     │   │  │
│  ├─────────────────────────┤  │ -bookTableModel       │   │  │
│  │ -studentRegNo           │  │ -borrowTable          │   │  │
│  │ -tableModel             │  │ -bookTable            │   │  │
│  │ -table                  │  ├───────────────────────┤   │  │
│  ├─────────────────────────┤  │ +AdminDashboardFrame()│   │  │
│  │ +StudentDashboardFrame()│  │ -createBorrowPanel()  │   │  │
│  │ -refreshBooks()         │  │ -createBooksPanel()   │   │  │
│  │ -onBorrow()             │  │ -refreshAll()         │   │  │
│  │ -onReturn()             │  │ -onAddBook()          │   │  │
│  └─────────────────────────┘  │ -onRemoveBook()       │   │  │
│                               └───────────────────────┘   │  │
│                                                           │  │
│  ┌──────────────────────────┐                            │  │
│  │     AddBookDialog        │                            │  │
│  ├──────────────────────────┤                            │  │
│  │ -titleField              │                            │  │
│  │ -countSpinner            │                            │  │
│  │ -success                 │                            │  │
│  ├──────────────────────────┤                            │  │
│  │ +AddBookDialog(JFrame)   │                            │  │
│  │ -onAdd()                 │                            │  │
│  │ +isSuccess()             │                            │  │
│  └──────────────────────────┘                            │  │
└───────────────────────────────────────────────────────────┘
```

## Page 2: Database, DAO, and Model Layers
                                 │
                                 │
                                 ▼
┌──────────────────────────────────────────────────────────────────┐
│                      DATABASE LAYER                               │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────┐                                        │
│  │      Database        │                                        │
│  ├──────────────────────┤                                        │
│  │ -DB_URL              │                                        │
│  │ -DB_USERNAME         │                                        │
│  │ -DB_PASSWORD         │                                        │
│  ├──────────────────────┤                                        │
│  │ +getConnection()     │                                        │
│  └───────┬──────────────┘                                        │
│          │                                                       │
│          ▼                                                       │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                      DAO LAYER                             │  │
│  ├───────────────────────────────────────────────────────────┤  │
│  │                                                           │  │
│  │  ┌──────────────────┐    ┌──────────────────┐            │  │
│  │  │    BookDao       │    │   StudentDao     │            │  │
│  │  ├──────────────────┤    ├──────────────────┤            │  │
│  │  │ +getAll()        │    │ +existsByRegNo() │            │  │
│  │  │ +findById()      │    │ +insertIfNot()   │            │  │
│  │  │ +findByTitle()   │    │ +upsertStudent() │            │  │
│  │  │ +insert()        │    └──────────────────┘            │  │
│  │  │ +upsertByTitle() │                                    │  │
│  │  │ +decrement()     │                                    │  │
│  │  │ +increment()     │    ┌──────────────────┐            │  │
│  │  │ +deleteById()    │    │    BorrowDao     │            │  │
│  │  └──────────────────┘    ├──────────────────┤            │  │
│  │                          │ +hasActive()     │            │  │
│  │                          │ +createBorrow()  │            │  │
│  │                          │ +returnBorrow()  │            │  │
│  │                          │ +listActive()    │            │  │
│  │                          └──────────────────┘            │  │
│  └───────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────────┘
                                 │
                                 │
                                 ▼
┌──────────────────────────────────────────────────────────────────┐
│                        MODEL LAYER                                │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────┐    ┌──────────────────┐                  │
│  │      Book        │    │     Student      │                  │
│  ├──────────────────┤    ├──────────────────┤                  │
│  │ -id              │    │ -regNo           │                  │
│  │ -title           │    │ -name            │                  │
│  │ -availableCount  │    ├──────────────────┤                  │
│  ├──────────────────┤    │ +Student()       │                  │
│  │ +getId()         │    │ +getRegNo()      │                  │
│  │ +getTitle()      │    │ +getName()       │                  │
│  │ +getAvailableCount│   └──────────────────┘                  │
│  │ +getAvailability()│                                         │
│  └──────────────────┘    ┌──────────────────┐                  │
│                          │  BorrowRecord    │                  │
│                          ├──────────────────┤                  │
│                          │ -id              │                  │
│                          │ -studentRegNo    │                  │
│                          │ -bookId          │                  │
│                          │ -bookTitle       │                  │
│                          │ -borrowedAt      │                  │
│                          │ -returnedAt      │                  │
│                          ├──────────────────┤                  │
│                          │ +getId()         │                  │
│                          │ +getStudentRegNo()│                 │
│                          │ +getBookTitle()  │                  │
│                          │ +getBorrowedAt() │                  │
│                          └──────────────────┘                  │
└──────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────┐
│                        DATABASE SCHEMA                            │
├──────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────┐                                           │
│  │      books       │                                           │
│  ├──────────────────┤                                           │
│  │ id (PK)          │                                           │
│  │ title (UNIQUE)   │                                           │
│  │ available_count  │                                           │
│  │ created_at       │                                           │
│  └──────────────────┘                                           │
│                                 ▲                               │
│                                 │                               │
│                          ┌──────┴──────┐                        │
│                          │   borrows   │                        │
│                          ├─────────────┤                        │
│                          │ id (PK)     │                        │
│  ┌──────────────────┐   │ student_reg │                        │
│  │    students      │   │ book_id     │                        │
│  ├──────────────────┤   │ borrowed_at │                        │
│  │ reg_no (PK)      │   │ returned_at │                        │
│  │ name             │   └─────────────┘                        │
│  │ created_at       │                                           │
│  └──────────┬───────┘                                           │
│             │                                                   │
│             └───────────────────────────────────────────────────┘
└──────────────────────────────────────────────────────────────────┘
```

## Relationships

### Inheritance & Composition
- **UI Frames** → All extend `JFrame` or `JDialog`
- **DAOs** → All use `Database.getConnection()`
- **Models** → Plain data classes

### Usage Dependencies
- **Main** → RoleSelectionFrame
- **RoleSelectionFrame** → StudentLoginFrame, AdminLoginFrame
- **StudentLoginFrame** → StudentDao → Database
- **AdminLoginFrame** → AdminDashboardFrame
- **StudentDashboardFrame** → BookDao, BorrowDao → Database
- **AdminDashboardFrame** → BookDao, BorrowDao → Database
- **AdminDashboardFrame** → AddBookDialog

### Data Flow
1. User actions in UI layer
2. UI calls DAO methods
3. DAOs execute SQL via Database connection
4. Data returned as Model objects
5. UI displays results in tables/dialogs


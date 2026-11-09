
const API_URL = "/api/employees";

let employees = [];

// Execute when the webpage loads
document.addEventListener("DOMContentLoaded", () => {
    loadEmployees();

    document.getElementById("employeeForm")
        .addEventListener("submit", handleFormSubmit);

    document.getElementById("refreshButton")
        .addEventListener("click", loadEmployees);

    document.getElementById("cancelEditButton")
        .addEventListener("click", resetForm);

    document.getElementById("employeeTableBody")
        .addEventListener("click", handleTableActions);
});


// 1. VIEW: Get all employees from REST API
async function loadEmployees() {
    const tableBody = document.getElementById("employeeTableBody");

    tableBody.textContent = "Loading employee records...";

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to load employee records");
        }

        employees = await response.json();

        displayEmployees(employees);
        updateDashboard(employees);

    } catch (error) {
        tableBody.textContent = "Failed to load employee records.";
        showMessage(error.message, "error");
    }
}


// Display employees in the HTML table
function displayEmployees(employeeList) {
    const tableBody = document.getElementById("employeeTableBody");

    tableBody.replaceChildren();

    if (employeeList.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");

        cell.colSpan = 5;
        cell.className = "empty-message";
        cell.textContent = "No employees found. Add your first employee.";

        row.appendChild(cell);
        tableBody.appendChild(row);
        return;
    }

    employeeList.forEach(employee => {
        const row = document.createElement("tr");

        const idCell = document.createElement("td");
        idCell.textContent = employee.id;

        const nameCell = document.createElement("td");
        nameCell.textContent = employee.name;

        const emailCell = document.createElement("td");
        emailCell.textContent = employee.email;

        const departmentCell = document.createElement("td");
        departmentCell.textContent = employee.department;

        const actionsCell = document.createElement("td");
        actionsCell.className = "action-buttons";

        const editButton = document.createElement("button");
        editButton.type = "button";
        editButton.textContent = "Edit";
        editButton.className = "btn btn-edit";
        editButton.dataset.action = "edit";
        editButton.dataset.id = employee.id;

        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.textContent = "Delete";
        deleteButton.className = "btn btn-delete";
        deleteButton.dataset.action = "delete";
        deleteButton.dataset.id = employee.id;

        actionsCell.append(editButton, deleteButton);

        row.append(
            idCell,
            nameCell,
            emailCell,
            departmentCell,
            actionsCell
        );

        tableBody.appendChild(row);
    });
}


// Update dashboard summary cards
function updateDashboard(employeeList) {
    document.getElementById("totalEmployees").textContent =
        employeeList.length;

    const departments = new Set(
        employeeList.map(employee => employee.department)
    );

    document.getElementById("totalDepartments").textContent =
        departments.size;
}


// 2. ADD / UPDATE: Handle employee form submission
async function handleFormSubmit(event) {
    event.preventDefault();

    const employeeId = document.getElementById("employeeId").value;

    const employee = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        department: document.getElementById("department").value
    };

    if (!employee.name || !employee.email || !employee.department) {
        showMessage("Please fill in all employee details.", "error");
        return;
    }

    const isEditing = employeeId !== "";

    const url = isEditing
        ? `${API_URL}/${employeeId}`
        : API_URL;

    const method = isEditing ? "PUT" : "POST";

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(employee)
        });

        if (!response.ok) {
            throw new Error(
                isEditing
                    ? "Failed to update employee."
                    : "Failed to add employee."
            );
        }

        showMessage(
            isEditing
                ? "Employee updated successfully!"
                : "Employee added successfully!",
            "success"
        );

        resetForm();
        await loadEmployees();

    } catch (error) {
        showMessage(error.message, "error");
    }
}


// 3. EDIT: Load employee details into the form
function editEmployee(id) {
    const employee = employees.find(
        item => String(item.id) === String(id)
    );

    if (!employee) {
        showMessage("Employee not found.", "error");
        return;
    }

    document.getElementById("employeeId").value = employee.id;
    document.getElementById("name").value = employee.name;
    document.getElementById("email").value = employee.email;
    document.getElementById("department").value = employee.department;

    document.getElementById("formTitle").textContent =
        "Edit Employee";

    document.getElementById("addEmployeeButton").textContent =
        "Update Employee";

    document.getElementById("cancelEditButton").hidden = false;

    document.getElementById("name").focus();
}


// 4. DELETE: Remove employee using REST API
async function deleteEmployee(id) {
    const confirmed = confirm(
        "Are you sure you want to delete this employee?"
    );

    if (!confirmed) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Failed to delete employee.");
        }

        showMessage("Employee deleted successfully!", "success");

        await loadEmployees();

    } catch (error) {
        showMessage(error.message, "error");
    }
}


// Handle Edit and Delete button clicks
function handleTableActions(event) {
    const button = event.target.closest("button[data-action]");

    if (!button) {
        return;
    }

    const id = button.dataset.id;
    const action = button.dataset.action;

    if (action === "edit") {
        editEmployee(id);
    } else if (action === "delete") {
        deleteEmployee(id);
    }
}


// Reset employee form
function resetForm() {
    document.getElementById("employeeForm").reset();

    document.getElementById("employeeId").value = "";

    document.getElementById("formTitle").textContent =
        "Add New Employee";

    document.getElementById("addEmployeeButton").textContent =
        "Add Employee";

    document.getElementById("cancelEditButton").hidden = true;
}


// Display success and error messages
function showMessage(message, type) {
    const messageElement = document.getElementById("message");

    messageElement.textContent = message;
    messageElement.className = `message ${type}`;
}

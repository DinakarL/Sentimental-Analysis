document.getElementById('feedbackForm').addEventListener('submit', function(event) {
    event.preventDefault();
    fetch('http://localhost:8080/api/givefeedback', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            name: document.getElementById('name').value,
            registerNumber: document.getElementById('registerNumber').value,
            department: document.getElementById('department').value,
            feedbackText: document.getElementById('feedbackText').value
        })
    }).then(response => response.text()).then(alert);
});

function fetchResults() {
    fetch('http://localhost:8080/api/showresult')
        .then(response => response.json())
        .then(data => alert(JSON.stringify(data)));
}
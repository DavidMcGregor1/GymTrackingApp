const closeButton = document.getElementById("closeBtn");

closeButton.addEventListener("click", () => {
  console.log("closed");
  window.location = "index.html";
});

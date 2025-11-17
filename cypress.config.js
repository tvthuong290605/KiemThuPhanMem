const { defineConfig } = require('cypress');

module.exports = defineConfig({
  e2e: {
    baseUrl: 'http://localhost:3000', // hoặc URL app của bạn
    specPattern: 'frontend/cypress/e2e/**/*.cy.{js,ts}',
    supportFile: false
  }
});
const m = require("./foo");

function regularFunction() {
    m.level1("hello")
}
exports.regularFunction = regularFunction;

async function asyncFunction() {
    m.level1("hello");
}
exports.asyncFunction = asyncFunction;
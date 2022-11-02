function level1(message) {
    level2(message);
}
exports.level1 = level1;

function level2(message) {
    const c = new TestClass();
    c.throwException(message);
}

class TestClass {
    throwException(message) {
        throw new Error(message);
    }
}
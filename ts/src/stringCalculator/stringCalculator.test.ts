import StringCalculator from "./stringCalculator";

test('should return 0', () => {
    expect(StringCalculator.Add("")).toStrictEqual(0);
});
test('should return 1', () => {
    expect(StringCalculator.Add("1")).toStrictEqual(1);
});
test('should return 3 with , delimiter', () => {
    expect(StringCalculator.Add("1,2")).toStrictEqual(3);
});
test('should return 15', () => {
    expect(StringCalculator.Add("1,2,3,4,5")).toStrictEqual(15);
});
test('should return 6 with \\n delimiter', () => {
    expect(StringCalculator.Add("1,2\n3")).toStrictEqual(6);
});
test('should return 6 with a custom delimiter', () => {
    expect(StringCalculator.Add("//;\n1,2;3")).toStrictEqual(6);
});

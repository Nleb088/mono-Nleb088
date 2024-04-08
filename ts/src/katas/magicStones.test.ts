import magicStones from "./magicStones";

test('should return empty array', () => {
    expect(magicStones([])).toStrictEqual([]);
});

test('should return [1,2]', () => {
    expect(magicStones([1, 1, 1]).sort((a: number, b: number) => (a - b))).toStrictEqual([1, 2]);
});

test('should return  [1,2]', () => {
    expect(magicStones([1, 2]).sort((a: number, b: number) => (a - b))).toStrictEqual([1, 2]);
});
test('should return [0]', () => {
    expect(magicStones([-1, -1]).sort((a: number, b: number) => (a - b))).toStrictEqual([0]);
});
test('should return  [7]', () => {
    expect(magicStones([5, 6, 1, 1, 2, 3, 4]).sort((a: number, b: number) => (a - b))).toStrictEqual([7]);
});
test('should return  [1,2,4]', () => {
    expect(magicStones([3, 1, 2, 3]).sort((a: number, b: number) => (a - b))).toStrictEqual([1, 2, 4]);
});
test('should return  [3]', () => {
    expect(magicStones([1, 1, 1, 1]).sort((a: number, b: number) => (a - b))).toStrictEqual([3]);
});
test('should return  [2,5]', () => {
    expect(magicStones([1, 3, 1, 3, 4]).sort((a: number, b: number) => (a - b))).toStrictEqual([2, 5]);
});
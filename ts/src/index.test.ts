import sum from "./index";

describe('testSum', () => {
    it('sum 3 + 4 should return 7', () => {
        expect(sum(3, 4)).toEqual(7);
    });
})


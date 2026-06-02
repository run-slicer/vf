import { decompile } from "../dist/vf.js";
import { argv } from "node:process";
import { readFile } from "node:fs/promises";

const numClasses = (argv.length - 2) / 2;
const namesAndPaths = argv.slice(2);
const names = namesAndPaths.slice(0, numClasses);
const paths = namesAndPaths.slice(numClasses);

const lookup = {};
for (let i = 0; i < numClasses; i++) {
    lookup[names[i]] = await readFile(paths[i]);
}

const name = names[0]; // output only the primary class (first)
const output = await decompile(name, {
    source: async (n) => {
        return lookup[n] ?? null;
    },
    resources: names,
});

console.log(output[name]);

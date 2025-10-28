const isNode = typeof process !== "undefined";
const wasmPath = async () => {
    const url = new URL("./vf.wasm", import.meta.url).href;
    return isNode ? await import("node:url").then((mod) => mod.fileURLToPath(url)) : url;
};

let decompileFunc = null;
export const decompile = async (name, options) => {
    if (!decompileFunc) {
        const { load } = await import("./runtime.js");
        const { exports } = await load(await wasmPath());

        decompileFunc = exports.decompile;
    }

    return decompileFunc(name, options);
};

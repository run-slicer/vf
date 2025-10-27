let decompileFunc = null;

export const decompile = async (name, options) => {
    if (!decompileFunc) {
        const { load } = await import("./runtime.js");
        const { exports } = await load(new URL("./vf.wasm", import.meta.url).href);

        decompileFunc = exports.decompile;
    }

    return decompileFunc(name, options);
};

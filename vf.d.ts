declare module "@run-slicer/vf" {
    export type Options = Record<string, string>;

    export interface Config {
        source?: (name: string) => Promise<Uint8Array | null>;
        resources?: string[];
        options?: Options;
    }

    export function decompile(name: string, config?: Config): Promise<string>;
}

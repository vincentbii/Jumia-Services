module.exports = function follow(api, rootPath, relArray) {
    const root = api({
        method: 'GET',
        path: rootPath,
        params: relArray
    });

    return traverseNext(root);

    function traverseNext(root) {
        return root.then(function (response) {
            if (hasEmbeddedRel(response.entity)) {
                return response.entity;
            }

            console.log("doing what??")

            if (!response.entity) {
                return [];
            }
        });
    }

    function hasEmbeddedRel(entity) {
        return entity && true;
    }
};
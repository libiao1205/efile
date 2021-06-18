const state = {
    history: [],
    curIndex: -1,
    moveFolder: null,
};

const actions = {
    addFolder({}, data) {
        if(!data){
            return;
        }
        if (state.curIndex === state.history.length - 1){
            state.history.push(data.id);
            state.curIndex++;
            state.moveFolder = null
        } else {
            state.history.splice(state.curIndex + 1, state.history.length - state.curIndex - 1);
            state.history.push(data.id);
            state.curIndex = state.history.length - 1;
            state.moveFolder = null
        }
    },

    addFolderById({}, id) {
        if (state.curIndex === state.history.length - 1){
            state.history.push(id);
            state.curIndex++;
            state.moveFolder = {id : id};
        } else {
            state.history.splice(state.curIndex + 1, state.history.length - state.curIndex - 1);
            state.history.push(id);
            state.curIndex = state.history.length - 1;
            state.moveFolder = {id : id};
        }
    },
    preFolder(){
        if (state.curIndex === 0) return;
        state.curIndex--;
        state.moveFolder = {id : state.history[state.curIndex]};
    },
    nextFolder({}){
        if (state.curIndex === state.history.length - 1) return;
        state.curIndex++;
        state.moveFolder = {id : state.history[state.curIndex]};
    },
    curFolder() {
        state.moveFolder = {id : state.history[state.curIndex]};
    }
};

export default {
    namespaced: true,
    state,
    actions
}

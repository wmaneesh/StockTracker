'use babel';

import Testing-ackageView from './testing--package-view';
import { CompositeDisposable } from 'atom';

export default {

  testing-ackageView: null,
  modalPanel: null,
  subscriptions: null,

  activate(state) {
    this.testing-ackageView = new Testing-ackageView(state.testing-ackageViewState);
    this.modalPanel = atom.workspace.addModalPanel({
      item: this.testing-ackageView.getElement(),
      visible: false
    });

    // Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    this.subscriptions = new CompositeDisposable();

    // Register command that toggles this view
    this.subscriptions.add(atom.commands.add('atom-workspace', {
      'testing--package:toggle': () => this.toggle()
    }));
  },

  deactivate() {
    this.modalPanel.destroy();
    this.subscriptions.dispose();
    this.testing-ackageView.destroy();
  },

  serialize() {
    return {
      testing-ackageViewState: this.testing-ackageView.serialize()
    };
  },

  toggle() {
    console.log('Testing-ackage was toggled!');
    return (
      this.modalPanel.isVisible() ?
      this.modalPanel.hide() :
      this.modalPanel.show()
    );
  }

};

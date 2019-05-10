/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Definitions for all standard api that are defined in specific
 * browsers extern files. We must eventually move these definition in one of
 * w3c_xyz.js files.
 *
 * @externs
 */

// Window API extension
/**
 * @param {*} message
 * @param {string} targetOrigin
 * @param {(!Array<!Transferable>)=} opt_transfer
 * @return {void}
 * @see https://developer.mozilla.org/en-US/docs/Web/API/Window/postMessage
 */
Window.prototype.postMessage = function(message, targetOrigin,
    opt_transfer) {};
// End of Window API extension

// WorkerGlobalScope API extension
/**
 * Worker postMessage method.
 * @param {*} message
 * @param {(!Array<!Transferable>)=} opt_transfer
 * @return {void}
 */
WorkerGlobalScope.prototype.postMessage = function(message, opt_transfer) {}
// WorkerGlobalScope API extension
